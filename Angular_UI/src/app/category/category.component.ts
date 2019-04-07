import { Component, OnInit } from '@angular/core';
import { Category } from '../category';
import { CategoryService } from '../services/category.service';
import { Observable } from 'rxjs/Observable';
import { RouterService } from '../services/router.service';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent {

  constructor(private categoryService: CategoryService,
  private routesService: RouterService) {

  }
  errMessage: string;
  category: Category = new Category();

  ngOnInit() {
  }

  takeCategory() {
    if (this.category.categoryName === '' && this.category.categoryDescription === '') {
      return Observable.throw(this.errMessage = 'Category Name and description both are required fields');
    }

    this.categoryService.addCategory(this.category).subscribe
      (
      data => { },
      err => this.errMessage = 'Http failure response for http://localhost:3000/api/v1/notes: 404 Not Found'
      );
    this.category = new Category();
   	this.routesService.routeToDashboard();
  }


}
