import { Component, NgZone, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { environment } from 'src/environments/environment';
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
      latitude: ['',Validators.required],
      longitude: ['',Validators.required],
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
          latitude:e.latLng.lat(),
          longitude:e.latLng.lng()
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
          this.removeControlsFromImageArrray();
        }
      },error=>{
  
      })
    }else{
      let updateProperty = {...this.propertyForm.value,id:this.editProperty.id};
      this.propertyService.updateProperty(updateProperty).subscribe(res=>{
        if(res){
          this.propertiesList.push(res);
          this.modalService.dismissAll();
          this.propertyForm.reset();
          this.removeControlsFromImageArrray();
        }
      },error=>{
  
      })
    }
    
  }

  onDelete(){
    this.propertyService.deleteProperty(this.editProperty.id).subscribe(res=>{
    },error=>{
      this.getProperties();
      this.modalService.dismissAll();
    });
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
            this.addImages(res);
          }
        },error=>{
  
        })
      });
    }
  }

  addImages(res) {
    this.images.push(this.fb.group({
      id:[res.id],
      name:[res.name],
      url:[res.url]
    }));
  }

  clickedMarker(property: any, index: number) {
    this.editProperty = property;
    debugger
    this.propertyForm.patchValue(property);
    debugger
    for(let img of property.images){
      let imgObj={
        image:environment.baseUrl+img.url,
        thumbImage:environment.baseUrl+img.url,
        title:img.name
      }
      this.imageObject.push(imgObj);
    }
    this.open(this.contentRef);
  }

  removeControlsFromImageArrray(){
    while (this.images.length) {
      this.images.removeAt(0);
    }
  }

}
