import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private apiUrl = 'http://localhost:8081/api/v1/auth';

  constructor(private http: HttpClient) { }

  login(username: string, password: string) {
    return this.http.post<any>(`${this.apiUrl}/authenticate`, {username, password});
  }
  register(username: string, email: string, password: string) {
    return this.http.post<any>(`${this.apiUrl}/register`, {username, email, password});
  }
}
