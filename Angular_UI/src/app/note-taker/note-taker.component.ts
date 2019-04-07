import { Component, OnInit } from '@angular/core';
import { Note } from '../note';
import { Category } from '../category';
import { NotesService } from '../services/notes.service';
import { CategoryService } from '../services/category.service';
import { Observable } from 'rxjs/Observable';
import { RouterService } from '../services/router.service';

@Component({
  selector: 'app-note-taker',
  templateUrl: './note-taker.component.html',
  styleUrls: ['./note-taker.component.css']
})
export class NoteTakerComponent {

  constructor(private noteservice: NotesService, private categoryService: CategoryService) {
  }
  errMessage: string;
  note: Note = new Note();
  category: Category = new Category();
  // declare the array
  notes: Array<Note> = [];
  categories: any;

  ngOnInit() {
   this.categoryService.getCategory().subscribe(
     data => this.categories = data,
     err => this.errMessage = 'Http failure response for getNotes(): 404 Not Found'
   );
  }

  takeNotes() {
    if (this.note.title === '' && this.note.text === '') {
      return Observable.throw(this.errMessage = 'Title and Text both are required fields');
    }
    this.notes.push(this.note);

    this.noteservice.addNote(this.note).subscribe
      (
      data => { },
      err => this.errMessage = 'Http failure response for http://localhost:3000/api/v1/notes: 404 Not Found'
      );
    this.note = new Note();
  }

}
