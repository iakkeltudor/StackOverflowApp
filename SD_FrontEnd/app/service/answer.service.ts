import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {Answer} from "../components/answer/answer.model";

@Injectable({
  providedIn: 'root'
})
export class AnswerService {
  constructor(private http: HttpClient) { }

  private apiUrl = 'http://localhost:8080/answers';
  getAnswers(questionId: number): Observable<Answer[]> {
    return this.http.get(`${this.apiUrl}/getAnswers/${questionId}`).pipe(
      map((response: any) => response as Answer[])
    );
  }
}
