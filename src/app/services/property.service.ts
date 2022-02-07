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
    return this.http.get(`${this.baseUrl}/location`);
  }

  getPropertyById(id):Observable<any>{
    return this.http.get(`${this.baseUrl}/location/${id}`);
  }

  saveProperty(obj):Observable<any>{
    return this.http.post(`${this.baseUrl}/location`,obj);
  }

  updateProperty(obj):Observable<any>{
    return this.http.put(`${this.baseUrl}/location`,obj);
  }

  saveImages(image):Observable<any>{
    const formData: FormData = new FormData();
    formData.append('image', image, image.name);
    return this.http.post(`${this.baseUrl}/image`,formData);
  }

  deleteProperty(id):Observable<any>{
    return this.http.delete(`${this.baseUrl}/location/${id}`);
  }

}
