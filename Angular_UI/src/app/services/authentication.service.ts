import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';


@Injectable()
export class AuthenticationService {

  constructor(private http: HttpClient) { }
  isUserAuthenticated(token): Promise<boolean> {
    return this.http.post('http://localhost:8765/api/UserAuthenticationService/api/v1/auth/isAuthenticated', {}, {
      headers: new HttpHeaders()
      .set('Authorization', `Bearer ${token}`)
    })
    .map((res) => res['isAuthenticated'])
    .toPromise();
   }

  authenticateUser(data) {
    console.log(data);
    return this.http.post('http://localhost:8765/api/UserAuthenticationService/api/v1/auth/login/', data);
  }

  setBearerToken(token) {
    localStorage.setItem('bearerToken', token);
  }

  getBearerToken() {
    return localStorage.getItem('bearerToken');
  }

  setUserId(userId) {
    localStorage.setItem('userId', userId);
  }

  getUserId() {
    return localStorage.getItem('userId');
  }
}
