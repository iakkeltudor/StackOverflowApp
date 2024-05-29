import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {UserService} from "../../service/user.service";
import {MatButton} from "@angular/material/button";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-ban-user-dialog',
  standalone: true,
  imports: [
    MatButton,
    FormsModule
  ],
  templateUrl: './ban-user-dialog.component.html',
  styleUrl: './ban-user-dialog.component.scss'
})
export class BanUserDialogComponent {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private userService: UserService, private dialogRef: MatDialogRef<BanUserDialogComponent>) { }

  reasonForBan: string = '';

  onBanClick() {
    this.userService.banUser(this.data.userId, this.reasonForBan).subscribe(() => {
      console.log('User banned');
    });
  }

  onCancelClick() {
    this.dialogRef.close();
  }
}
