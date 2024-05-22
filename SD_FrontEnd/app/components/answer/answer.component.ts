import {Component, OnInit} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {Question} from "../question/question.model";
import {QuestionService} from "../../service/question.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ImageService} from "../../service/image.service";
import {Answer} from "./answer.model";
import {AnswerService} from "../../service/answer.service";

@Component({
  selector: 'app-answer',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    FormsModule
  ],
  templateUrl: './answer.component.html',
  styleUrl: './answer.component.scss'
})
export class AnswerComponent implements OnInit {
  question: Question | undefined;
  currentDate: string | undefined;
  currentTime: string | undefined;
  answer: string = '';

  answers: Answer[] = [];

  constructor(
    private questionService: QuestionService,
    private answerService: AnswerService,
    private route: ActivatedRoute,
    private router: Router,
    private imageService: ImageService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      if (params && params['question']) {
        this.question = JSON.parse(params['question']);
        if(this.question) {
          const answer = this.question.answerSet[0];
          if(answer) {
            const [year, month, day, hour, minute] = answer.creationDateTime;
            const date = `${year}-${month}-${day}`;
            const time = `${hour}:${minute}`;
            answer.date = date;
            answer.time = time;
          }
          this.retrieveAnswers(this.question.id);
        }
      } else {
        //ceva
      }
    });
  }

  logout() {
    this.router.navigate(['/login']);
  }

  goToUser() {
    this.router.navigate(['/user']);
  }

  goToHome() {
    this.router.navigate(['/question']);
  }

  retrieveAnswers(questionId: number) {
    this.answerService.getAnswers(questionId).subscribe((data: Answer[]) => {
      this.answers = data;
    });
  }

  submitAnswer() {

  }


}
