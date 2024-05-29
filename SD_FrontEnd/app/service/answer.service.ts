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

  createAnswer(answer: {
    questionId: number | undefined;
    imagePath: string;
    text: string;
    authorId: number;
    creationDateTime: string | null
  }): Observable<Answer> {
    return this.http.post<Answer>(`${this.apiUrl}/insertAnswer`, answer);
  }

  deleteAnswer(id: number | undefined): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/deleteAnswer/${id}`);
  }

  updateAnswer(answer: {
    imagePath: string;
    questionId: string;
    text: string;
    authorId: string;
    creationDateTime: string | null
  }, id: number): Observable<Answer> {
    return this.http.put<Answer>(`${this.apiUrl}/updateAnswer/${id}`, answer);
  }

  upVote(answerId: number, userId: number): Observable<void> {
    const VoteRequest = {userId: userId};
    return this.http.post<void>(`${this.apiUrl}/upVote/${answerId}`, VoteRequest);
  }

  downVote(answerId: number, userId: number): Observable<void> {
    const VoteRequest = {userId: userId};
    return this.http.post<void>(`${this.apiUrl}/downVote/${answerId}`, VoteRequest);
  }
}
