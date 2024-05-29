import {Component, OnInit} from '@angular/core';
import {DatePipe, NgForOf, NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {Question} from "../question/question.model";
import {QuestionService} from "../../service/question.service";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {ImageService} from "../../service/image.service";
import {Answer} from "./answer.model";
import {AnswerService} from "../../service/answer.service";
import {User} from "../user/user.model";
import {UserService} from "../../service/user.service";

@Component({
  selector: 'app-answer',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    FormsModule,
    RouterLink
  ],
  templateUrl: './answer.component.html',
  styleUrl: './answer.component.scss',
  providers: [DatePipe]
})
export class AnswerComponent implements OnInit {
  question: Question | undefined;
  currentDate: string | undefined;
  currentTime: string | undefined;
  answer: string = '';
  loggedInUserId: number = 0;

  answers: Answer[] = [];
  answerText: string = '';
  private picture: File | null = null;
  private imagePath: string = '';
  private authorId: number = 0;
  loggedInUser: User = new User();

  constructor(
    private questionService: QuestionService,
    private userService: UserService,
    private answerService: AnswerService,
    private route: ActivatedRoute,
    private router: Router,
    private imageService: ImageService,
    private datePipe: DatePipe
  ) {}

  ngOnInit(): void {
    this.loggedInUserId = parseInt(localStorage.getItem('userId') || '', 10);

    if(!this.loggedInUserId) {
      this.logout();
    }

    this.userService.getUserById(this.loggedInUserId).subscribe((data: any) => {
      this.loggedInUser = data;
    });

    this.route.queryParams.subscribe(params => {
      if (params && params['question']) {
        this.question = JSON.parse(params['question']);
        if(this.question) {
          if (this.question.imagePath) {
            this.loadImage(this.question);
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
      this.answers = data.map(answer => {
        const [year, month, day, hour, minute] = answer.creationDateTime;
        const date = `${year}-${month}-${day}`;
        const time = `${hour}:${minute.toString().padStart(2, '0')}`;
        answer.date = date;
        answer.time = time;
        if(answer.imagePath) {
          this.loadImage(answer);
        }
        return answer;
      });
    });
  }

  private loadImage(answer: any) {
    this.imageService.getImage(answer.imagePath).subscribe(blob => {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        answer.imageUrl = e.target.result;
      };
      reader.readAsDataURL(blob);
    });
  }

  loadQuestionDetails(question: Question | undefined) {
    // @ts-ignore
    this.answerService.getAnswers(question.id).subscribe((data: Answer[]) => {
      this.answers = data;
    });
  }

  editAnswer(answer: Answer) {
    this.router.navigate(['/edit-answer'], {
      queryParams: {
        id: answer.id,
        questionId: answer.questionId,
        text: answer.text,
        imagePath: answer.imagePath,
        imageUrl: answer.imageUrl
      }
    });
  }

  deleteAnswer(answerId: number | undefined) {
    if (confirm('Are you sure you want to delete this answer?')) {
      this.answerService.deleteAnswer(answerId).subscribe(
        () => {
          alert('Answer deleted successfully.');
          // Refresh the page to reflect the deleted answer
          this.loadQuestionDetails(this.question);
        }
      );
    }
  }

  submitAnswer() {
    if (!this.answerText || !this.picture) {
      alert('Please fill in all the information.');
      return;
    }

    const currentDate = new Date();
    const creationDateTime = this.datePipe.transform(
      currentDate,
      'yyyy-MM-dd HH:mm'
    );

    this.onUpload();

    const answerData = {
      questionId: this.question?.id,
      text: this.answerText,
      imagePath: this.imagePath,
      authorId: this.loggedInUserId,
      creationDateTime: creationDateTime
    };

    this.answerService.createAnswer(answerData).subscribe(
      () => {
        alert('Answer submitted successfully.');
        this.loadQuestionDetails(this.question);
        this.answerText = '';
        this.imagePath = '';
      }
    );
  }

  onImageSelected(event: any) {
    this.picture = event.target.files[0];
    // @ts-ignore
    this.imagePath = this.picture.name;

  }

  onUpload() {
    if (this.picture) {
      this.imageService.uploadImage(this.picture).subscribe();
    }
  }

  upvoteQuestion(question: Question | undefined) {
    // @ts-ignore
    this.questionService.upVote(question.id, this.loggedInUserId).subscribe();
  }

  downvoteQuestion(question: Question | undefined) {
    // @ts-ignore
    this.questionService.downVote(question.id, this.loggedInUserId).subscribe();
  }

  upvoteAnswer(answer: Answer) {
    // @ts-ignore
    this.answerService.upVote(answer.id, this.loggedInUserId).subscribe();
  }

  downvoteAnswer(answer: Answer) {
    // @ts-ignore
    this.answerService.downVote(answer.id, this.loggedInUserId).subscribe();
  }


}
