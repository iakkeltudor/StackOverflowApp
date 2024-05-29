import {HttpClient, HttpHeaders} from "@angular/common/http";
import { Observable } from "rxjs";
import { Injectable } from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class ImageService {
  private uploadUrl = 'http://localhost:8080/images/upload';
  private baseUrl = 'http://localhost:8080/images/';
  constructor(private http: HttpClient) { }

  uploadImage(file: File): Observable<any> {
    let formParams = new FormData();
    formParams.append('file', file);
    return this.http.post(this.uploadUrl, formParams);
  }

  getImage(fileName: string): Observable<Blob> {
    return this.http.get(`${this.baseUrl}${fileName}`, { responseType: 'blob' });
  }
}
