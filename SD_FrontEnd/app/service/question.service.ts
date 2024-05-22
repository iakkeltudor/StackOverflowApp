import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Question} from "../components/question/question.model";

@Injectable({
  providedIn: 'root'
})
export class QuestionService {
  private apiUrl = 'http://localhost:8080/questions';

  constructor(private http: HttpClient) { }

  getQuestions() : Observable<Question[]> {
    return this.http.get<Question[]>(`${this.apiUrl}/getAll`);
  }

  createQuestion(question: {
    imagePath: string;
    text: string;
    title: string;
    authorId: string;
    tags: string[];
    creationDateTime: string | null
  }) : Observable<Question> {
    return this.http.post<Question>(`${this.apiUrl}/insertQuestion`, question);
  }

  deleteQuestion(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/deleteQuestion/${id}`);
  }

  updateQuestion(question: {
    text: string;
    title: string;
    tags: string[];
  }, id : number) : Observable<Question> {
    return this.http.put<Question>(`${this.apiUrl}/updateQuestion/${id}`, question);
  }
}
