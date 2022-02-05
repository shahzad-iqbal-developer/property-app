import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PropertyService {

  private baseUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  getProperties():Observable<any>{
    return this.http.get(`${this.baseUrl}/properties`);
  }

  getPropertyById(id):Observable<any>{
    return this.http.get(`${this.baseUrl}/properties/${id}`);
  }

  saveProperty(obj):Observable<any>{
    return this.http.post(`${this.baseUrl}/properties`,obj);
  }

  updateProperty(id,obj):Observable<any>{
    return this.http.put(`${this.baseUrl}/properties/${id}`,obj);
  }

  saveImages(image):Observable<any>{
    // const formData: FormData = new FormData();
    // formData.append('images', image, image.name);
    return this.http.post(`${this.baseUrl}/images`,{name:image.name});
  }

}
