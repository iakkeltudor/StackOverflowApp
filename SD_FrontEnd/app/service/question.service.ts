import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Question} from "../components/question/question.model";
import {Tag} from "../components/tag/tag.model";

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
    imagePath: string;
    text: string;
    title: string;
    authorId: string;
    tags: string[];
    creationDateTime: string | null
  }, id: number) : Observable<Question> {
    return this.http.put<Question>(`${this.apiUrl}/updateQuestion/${id}`, question);
  }

  upVote(questionId: number, userId: number | undefined): Observable<string> {
    const VoteRequest = {userId: userId};
    return this.http.post<string>(`${this.apiUrl}/upVote/${questionId}`, VoteRequest);
  }

  downVote(questionId: number, userId: number | undefined): Observable<string> {
    const VoteRequest = {userId: userId};
    return this.http.post<string>(`${this.apiUrl}/downVote/${questionId}`, VoteRequest);
  }

  getQuestionByText(title: string): Observable<Question[]> {
    return this.http.get<Question[]>(`${this.apiUrl}/getByTitle/${title}`);
  }

  getQuestionByTag(tag: string): Observable<Question[]> {
    return this.http.get<Question[]>(`${this.apiUrl}/getByTag/${tag}`);
  }

  getQuestionByUsername(username: string): Observable<Question[]> {
    return this.http.get<Question[]>(`${this.apiUrl}/getByUsername/${username}`);
  }

  getOwnQuestions(userId: number | undefined): Observable<Question[]> {
    return this.http.get<Question[]>(`${this.apiUrl}/getOwnQuestions/${userId}`);
  }
}
