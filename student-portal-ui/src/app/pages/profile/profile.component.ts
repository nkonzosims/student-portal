import {Component, inject, OnInit, signal} from '@angular/core';
import {FormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import {AuthService, UpdateProfileInt} from '../../services/auth/auth.service';
import {tap} from 'rxjs';
import {NgClass} from '@angular/common';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgClass
  ],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent implements OnInit {

  formBuilder = inject(FormBuilder);
  authService = inject(AuthService);

  formStatus = 'default';

  profileForm = this.formBuilder.group({
    firstName: [{ value: '', disabled: true }, Validators.required],
    lastName: [{ value: '', disabled: true }, Validators.required],
    dateOfBirth: [{ value: '', disabled: true }, Validators.required],
    email: [{ value: '', disabled: true }, [Validators.required, Validators.email]],
    phoneNumber: ['', Validators.required],
    idNumber: [{ value: '', disabled: true }],
    passportNumber: [{ value: '', disabled: true }],
    studentNumber: [{ value: '', disabled: true }, Validators.required],
    password: ['', Validators.required]
  });

  constructor() { }

  ngOnInit() {
    // Call the service to get the profile data
    this.authService.getProfile().subscribe(profile => {
      this.profileForm.patchValue(profile);
    });

    this.profileForm.valueChanges.subscribe(() => {
      this.formStatus = 'default';
    });
  }

  updateProfile(): void {
    if (this.profileForm.valid) {
      this.authService.updateProfile(this.profileForm.getRawValue() as UpdateProfileInt)
        .pipe(
          tap(
            response => {
              this.formStatus = 'success';
              console.log('Profile update successful', response);
            },
            error => {
              this.formStatus = 'error';
              console.error('Profile update failed', error);
            }
          )
      ).subscribe();
    }
  }
}
