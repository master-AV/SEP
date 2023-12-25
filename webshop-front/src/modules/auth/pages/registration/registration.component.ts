import { Component, OnDestroy, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  FormGroupDirective,
  NgForm,
  ValidatorFn,
  Validators
} from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';
import { UserService } from 'src/modules/auth/service/user-service/user.service';
import { ToastrService } from 'ngx-toastr';
import { User } from '../../model/user';
import { matchPasswordsValidator } from '../../validators/confirm-password.validator';
import { UserRegistrationRequest } from '../../model/registration_and_verification/regular-user-registration';


@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit, OnDestroy {

  showSpiner: boolean = false;
  hidePassword = true;
  hideConfirmPassword = true;
  registrationSubscription: Subscription;
  registrationForm: FormGroup;

  constructor(
    private toast: ToastrService,
    private router: Router,
    private userService: UserService
  ) {
    this.showSpiner = false;
  }

  ngOnInit(): void {
    this.registrationForm = new FormGroup(
      {
        emailFormControl: new FormControl('', [
          Validators.required,
          Validators.email,
        ]),
        nameFormControl: new FormControl('', [
          Validators.required,
          Validators.pattern('[a-zA-Z ]*'),
        ]),
        surnameFormControl: new FormControl('', [
          Validators.required,
          Validators.pattern('[a-zA-Z ]*'),
        ]),
        passwordFormControl: new FormControl('', [
          Validators.required,
          Validators.minLength(12),
          Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{12,100}$')
        ]),
        passwordAgainFormControl: new FormControl('', [
          Validators.required,
          Validators.minLength(12),
          Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{12,100}$')
        ])
      },
      [matchPasswordsValidator()]
    );
  }

  matcher = new MyErrorStateMatcher();

  register(): void {
    const newUser: UserRegistrationRequest = {
      email: this.registrationForm.get('emailFormControl').value,
      name: this.registrationForm.get('nameFormControl').value,
      surname: this.registrationForm.get('surnameFormControl').value,
      password: this.registrationForm.get('passwordFormControl').value,
      confirmPassword: this.registrationForm.get('passwordAgainFormControl').value,
      role: 'ROLE_USER'
    };
    if (this.registrationForm.hasError('mismatch')) {
      this.toast.error('Passwords not match');
    } else if (!this.registrationForm.valid) {
      Object.keys(this.registrationForm.controls).forEach(field => {
        const control = this.registrationForm.get(field);
        console.log(control.value, control.invalid);

      });
      this.toast.error('Registration form is invalid.')
    } else {
      const newUser: UserRegistrationRequest = {
        email: this.registrationForm.get('emailFormControl').value,
        name: this.registrationForm.get('nameFormControl').value,
        surname: this.registrationForm.get('surnameFormControl').value,
        password: this.registrationForm.get('passwordFormControl').value,
        confirmPassword: this.registrationForm.get('passwordAgainFormControl').value,
        role: 'ROLE_USER'
      };

      this.showSpiner = true;
      this.registrationSubscription = this.userService
            .registerUser(newUser)
            .subscribe(
              response => {
                this.showSpiner = false;
                this.toast.success(
                  'Please go to your email to verify account!',
                  'Registration successfully'
                );
                this.router.navigate([`/auth/login`]);
              },
              error => {
                this.showSpiner = false;
                this.toast.error(error.error, 'Registration failed')
              }
            );
    }
  }

  getError() {
    return this.registrationForm.hasError('mismatch');
  }

  ngOnDestroy(): void {
    if (this.registrationSubscription) {
      this.registrationSubscription.unsubscribe();
    }
  }

}

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(
    control: FormControl | null,
    form: FormGroupDirective | NgForm | null
  ): boolean {
    const isSubmitted = form && form.submitted;
    return !!(
      control &&
      control.invalid &&
      (control.dirty || control.touched || isSubmitted)
    );
  }

}
