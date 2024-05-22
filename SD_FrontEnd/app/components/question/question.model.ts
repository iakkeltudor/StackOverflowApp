import {User} from "../user/user.model";
import {Tag} from "../tag/tag.model";
import {Answer} from "../answer/answer.model";

export class Question{
  answerSet: Answer[] = [new Answer()]
  userSet: string[] = ["Dummy"]
  authorId: number = 0
  author: User = new User()
  title: string = "Dummy"
  text: string = "Dummy"
  time: string = "Dummy"
  date: string = "Dummy"
  tags: Tag[] = [new Tag()]
  imagePath: string = "Dummy"
  imageUrl: string = "Dummy"
  hovered: boolean = false
  id: number = 0
}
