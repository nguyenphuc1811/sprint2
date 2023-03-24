import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {User} from "../../entity/user";
import {Observable} from "rxjs";
import {AngularFireStorage} from "@angular/fire/storage";
import {ShareService} from "../../service/user/share.service";
import {TokenService} from "../../service/user/token.service";
import {Router} from "@angular/router";
import {LoginService} from "../../service/user/login.service";
import {Title} from "@angular/platform-browser";
import Swal from 'sweetalert2';
import {finalize} from "rxjs/operators";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  nameError = '';
  phoneNumberError = '';
  emailError = '';
  addressError = '';
  ageError = '';
  genderError = '';
  dateOfBirthError = '';
  avatarError = '';
  user: User;
  form = new FormGroup({
    name: new FormControl(),
    phoneNumber: new FormControl(),
    email: new FormControl(),
    address: new FormControl(),
    gender: new FormControl(),
    age: new FormControl(),
    dateOfBirth: new FormControl(),
    avatar: new FormControl()
  })
  role = '';
  formPassword = new FormGroup({
    password: new FormControl(),
    newPassword: new FormControl(),
    confirmPassword: new FormControl()
  })
  downloadURL: Observable<string> | undefined;
  fb: string | undefined;
  src: string | undefined;

  constructor(
    // private storage: AngularFireStorage,
               private share: ShareService, private token: TokenService, private router: Router, private userService: LoginService, private title: Title) {

  }

  ngOnInit(): void {
    window.scroll(0,980)
    this.title.setTitle('Trang cá nhân');
    if (!this.token.isLogger()) {
      this.router.navigateByUrl('/home')
    } else {
      this.getInfo();
      this.getValue();
    }
  }

  getValue() {
    this.form.controls.name.patchValue(this.user.name);
    this.form.controls.phoneNumber.patchValue(this.user.phoneNumber);
    this.form.controls.email.patchValue(this.user.email);
    this.form.controls.gender.patchValue(this.user.gender);
    this.form.controls.address.patchValue(this.user.address);
    // @ts-ignore
    let timeDiff = Math.abs(Date.now() - new Date(this.user.dateOfBirth));
    this.form.controls.age.patchValue(Math.floor((timeDiff / (1000 * 3600 * 24)) / 365))
    this.form.controls.dateOfBirth.patchValue(this.user.dateOfBirth);
    this.form.controls.avatar.patchValue(this.user.avatar);
  }


  getInfo() {
    this.userService.profile(this.token.getUsername()).subscribe(
      next => {
        this.user = next;
        // @ts-ignore
        let timeDiff = Math.abs(Date.now() - new Date(this.user.dateOfBirth));
        this.user.age = Math.floor((timeDiff / (1000 * 3600 * 24)) / 365);
        // this.role = this.user.role;
        console.log(this.token.getRole())
        this.getValue();

      }
    )
  }

  update() {
    this.nameError = '';
    this.phoneNumberError = '';
    this.emailError = '';
    this.addressError = '';
    this.ageError = '';
    this.genderError = '';
    this.dateOfBirthError = '';
    this.avatarError = '';
    // @ts-ignore
    let timeDiff = Math.abs(Date.now() - new Date(this.form.controls.dateOfBirth.value));
    this.form.controls.age.patchValue(Math.floor((timeDiff / (1000 * 3600 * 24)) / 365))
    this.userService.updateUser(this.form.value).subscribe(next => {
      document.getElementById('dismiss').click()
      Swal.fire({
        position: 'center',
        icon: 'success',
        title: 'Chúc mừng ' + this.form.controls.name.value + ' đã cập nhật thông tin thành công',
        showConfirmButton: false,
        timer: 2500
      })
      this.share.sendClickEvent();
      this.getInfo();
    }, error => {
      Swal.fire({
        position: 'center',
        icon: 'error',
        title: 'Vui lòng điền đầy đủ thông tin vào ông trống',
        showConfirmButton: false,
        timer: 2500
      })
      for (let i = 0; i < error.error.length; i++) {
        if (error.error[i].field == 'name') {
          this.nameError = error.error[i].defaultMessage;
        } else if (error.error[i].field == 'phoneNumber') {
          this.phoneNumberError = error.error[i].defaultMessage;
        } else if (error.error[i].field == 'email') {
          this.emailError = error.error[i].defaultMessage;
        } else if (error.error[i].field == 'address') {
          this.addressError = error.error[i].defaultMessage;
        } else if (error.error[i].field == 'age') {
          this.ageError = error.error[i].defaultMessage;
        } else if (error.error[i].field == 'dateOfBirth') {
          this.dateOfBirthError = error.error[i].defaultMessage;
        } else if (error.error[i].field == 'avatar') {
          this.avatarError = error.error[i].defaultMessage;
        }
      }
    })
  }

  selectedImage: any = null;

  // showPreview(event: any) {
  //   this.selectedImage = event.target.files[0];
  //   const filePath = this.selectedImage.name;
  //   const fileRef = this.storage.ref(filePath);
  //   const task = this.storage.upload(filePath, this.selectedImage);
  //   task
  //     .snapshotChanges()
  //     .pipe(
  //       finalize(() => {
  //         this.downloadURL = fileRef.getDownloadURL();
  //         this.downloadURL.subscribe(url => {
  //           if (url) {
  //             // lấy lại url
  //             this.user.avatar = url;
  //           }
  //           this.form.patchValue({avatar: url});
  //           this.src = url;
  //           // console.log('link: ', this.fb);
  //         });
  //       })
  //     )
  //     .subscribe();
  // }

  passwordError = '';
  newPasswordError = '';
  confirmPasswordError = '';

  changePassword() {
    this.passwordError = '';
    this.newPasswordError = '';
    this.confirmPasswordError = '';
    this.userService.changePassword(this.formPassword.value).subscribe(
      next => {
        Swal.fire({
          position: 'center',
          icon: 'success',
          title: 'Chúc mừng ' + this.user.name + ' đã cập nhật mật khẩu thành công',
          showConfirmButton: false,
          timer: 2500
        })
        document.getElementById('dismiss2').click()
      }, error => {
        console.log(error)
        Swal.fire({
          position: 'center',
          icon: 'error',
          title: 'Thay đổi mật khẩu thất bại',
          showConfirmButton: false,
          timer: 2500
        })
        for (let i = 0; i < error.error.length; i++) {
          if (error.error[i].field == 'password') {
            this.passwordError = error.error[i].defaultMessage;
          } else if (error.error[i].field == 'newPassword') {
            this.newPasswordError = error.error[i].defaultMessage;
          } else if (error.error[i].field == 'confirmPassword') {
            this.confirmPasswordError = error.error[i].defaultMessage;
          }
        }
      }
    )
  }
}
