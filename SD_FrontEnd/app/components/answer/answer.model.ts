import { User } from "../user/user.model";

export class Answer {
  id: number = 0;
  questionId: number = 0;
  text: string = '';
  date: string = '';
  time: string = '';
  creationDateTime: string = '';
  author: User = new User();
  authorId: number = 0;
  imagePath: string = '';
  imageUrl: string = '';
  score: number = 0;
}
