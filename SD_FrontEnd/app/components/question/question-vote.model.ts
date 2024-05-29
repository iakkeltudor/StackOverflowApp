import { User } from "../user/user.model";
import {Question} from "./question.model";

export class QuestionVote {
  questionId: number = 0;
  userId: number = 0;
  vote: string = "";
  id: number = 0;
}
