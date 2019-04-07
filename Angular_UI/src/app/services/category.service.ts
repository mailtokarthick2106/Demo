import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Observable } from 'rxjs/Observable';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Note } from '../note';
import { Category } from '../category';
import { AuthenticationService } from '../services/authentication.service';
import { tap } from 'rxjs/operators';

const API_URL = 'http://localhost:8765/api/CategoryService/api/v1/category';

@Injectable()
export class CategoryService {
  categories: Array<Category>;
   category: Category = new Category();
   categoryName: string;
   categorySubject: BehaviorSubject<Array<string>>;
   categorySubjectone: Array<string>;
  token: any;
  constructor(private http: HttpClient, private _authService: AuthenticationService) {
    this.categories = [];
    this.categorySubject = new BehaviorSubject(this.categorySubjectone);
  }

  fetchCategoryFromServer() {
  console.log('called');
    this.token = this._authService.getBearerToken();
    return this.http.get<Array<Category>>(API_URL, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${this.token}`)
    }).subscribe(categories => {
      this.categories = categories;
      this.categorySubjectone = this.categories.map( el => el.categoryName);
      this.categorySubject.next(this.categorySubjectone);
    });
  }

  getCategory(): BehaviorSubject<Array<string>> {
     return this.categorySubject;
   }

   addCategory(category: Category): Observable<Category> {
     return this.http.post<Category>(API_URL, category, {
       headers: new HttpHeaders().set('Authorization', `Bearer ${this._authService.getBearerToken()}`)
     }).pipe(tap(data => {
       this.categories.push(data);
     }
     ));
  }
}
