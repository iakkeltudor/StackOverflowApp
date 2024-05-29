import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/users';

  constructor(private http: HttpClient) { }

  getPoints(userId: number | undefined): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/getPoints/${userId}`);
  }

  getAllUsers(): Observable<any> {
    return this.http.get(`${this.apiUrl}/getAll`);
  }

  getUserById(userId: number | undefined): Observable<any> {
    return this.http.get(`${this.apiUrl}/getById/${userId}`);
  }

  banUser(userId: number | undefined, reason: string): Observable<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.http.post(`${this.apiUrl}/ban/${userId}`, {reason}, {headers});
  }

  unBanUser(userId: number | undefined): Observable<any> {
    // @ts-ignore
    return this.http.post(`${this.apiUrl}/unban/${userId}`);

  }

  checkBan(username: string): Observable<any> {
    let number = this.http.get(`${this.apiUrl}/checkBan/${username}`);
    console.log('number: ', number);
    return number;
  }
}
