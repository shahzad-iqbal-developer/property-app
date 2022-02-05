import { Component, NgZone, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PropertyService } from './services/property.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  @ViewChild("content") private contentRef: TemplateRef<Object>;
  lat: number;
  lng: number;
  zoom: number;
  propertyForm : FormGroup;
  map: google.maps.Map<Element>;
  mapClickListener: google.maps.MapsEventListener;
  closeResult: string;
  propertiesList = [];
  editProperty: any;
  imageObject = [];

  constructor(
    private fb:FormBuilder,
    private zone:NgZone,
    private modalService: NgbModal,
    private propertyService:PropertyService
    ){
    this.lat = 24.8607;
    this.lng = 67.0011;
    this.zoom = 15;
    this.propertyForm = this.fb.group({
      roomNumber: ['',Validators.required],
      square: ['',Validators.required],
      description: ['',Validators.required],
      address: ['',Validators.required],
      lat: ['',Validators.required],
      lng: ['',Validators.required],
      images: this.fb.array([])
    })
  }

  ngOnInit(): void {
    this.getProperties();
  }

  getProperties(){
    this.propertyService.getProperties().subscribe(res=>{
      if(res){
        this.propertiesList = res;
      }
    },error=>{

    })
  }

  get images() {
    return this.propertyForm.get('images') as FormArray;
  }

  public mapReadyHandler(map: google.maps.Map): void {
    this.map = map;
    this.mapClickListener = this.map.addListener('click', (e: google.maps.MouseEvent) => {
      this.zone.run(() => {
        this.open(this.contentRef);
        this.propertyForm.patchValue({
          lat:e.latLng.lat(),
          lng:e.latLng.lng()
        })
      });
    });
  }

  onSubmit(){
    if(!this.editProperty){
      this.propertyService.saveProperty(this.propertyForm.value).subscribe(res=>{
        if(res){
          this.propertiesList.push(res);
          this.modalService.dismissAll();
          this.propertyForm.reset();
        }
      },error=>{
  
      })
    }else{
      this.propertyService.updateProperty(this.editProperty.id,this.propertyForm.value).subscribe(res=>{
        if(res){
          this.propertiesList.push(res);
          this.modalService.dismissAll();
          this.propertyForm.reset();
        }
      },error=>{
  
      })
    }
    
  }

  open(content) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
      this.editProperty = false;
      this.propertyForm.reset();
      this.imageObject=[]
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
      this.editProperty = false;
      this.propertyForm.reset();
      this.imageObject=[]
    });
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return  `with: ${reason}`;
    }
  }

  handleFileUpload(files: FileList) {
    if(files && files.length) {
      Array.from(files).forEach(file => { 
        this.propertyService.saveImages(file).subscribe(res=>{
          if(res){
            this.addImages(res.name);
          }
        },error=>{
  
        })
      });
    }
  }

  addImages(image) {
    this.images.push(this.fb.control(image));
  }

  clickedMarker(property: any, index: number) {
    this.editProperty = property;
    this.propertyForm.patchValue(property);
    for(let img of property.images){
      let imgObj={
        image:img,
        thumbImage:img,
        title:img
      }
      this.imageObject.push(imgObj);
    }
    this.open(this.contentRef);
  }

}
