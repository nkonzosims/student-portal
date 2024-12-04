import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {of} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  httpClient = inject(HttpClient);

  constructor() { }

  register(registrationData: RegistrationInt) {
    console.log('registrationData on register', registrationData);
    return this.httpClient.post('http://localhost:8081/api/auth/register', registrationData);
  }

  login(loginData: LoginInt) {
    console.log('loginData on login', loginData);
    this.setLoggedIn(true);
    return this.httpClient.post('http://localhost:8081/api/auth/login', loginData);
  }

  verifyAccount(verificationData: VerificationInt) {
    return this.httpClient.post('http://localhost:8081/api/auth/verify', verificationData);
  }

  getProfile() {
    return of(
      {
        phoneNumber: '0799997942',
        firstName: 'Nkonzo',
        lastName: 'Simelane',
        dateOfBirth: '1990-01-01',
        email: 'nkonzosimelane@email.com',
        idNumber: '9001011234567',
        passportNumber: 'A1234567',
        studentNumber: '214224755'
      }
    )
  }

  updateProfile(profileData: UpdateProfileInt) {
    return this.httpClient.put('http://localhost:3000/auth/update', profileData);
  }

  isLoggedIn(): boolean {
    return !!sessionStorage.getItem('isLoggedIn');
  }

  setLoggedIn(status: boolean) {
    sessionStorage.setItem('isLoggedIn', status ? 'true' : 'false');
  }
}

export interface LoginInt {
  email: string;
  password: string;
}

export interface VerificationInt {
  email: string;
  verificationCode: string;
}

export interface RegistrationInt {
  firstName: string;
  lastName: string;
  dateOfBirth: Date;
  email: string;
  password: string;
  phoneNumber: string;
  idNumber?: string;
  passportNumber?: string;
}

export interface UpdateProfileInt {
  firstName: string;
  lastName: string;
  dateOfBirth: string;
  email: string;
  password: string;
  phoneNumber: string;
  studentNumber: string;
  idNumber?: string;
  passportNumber?: string;
}
