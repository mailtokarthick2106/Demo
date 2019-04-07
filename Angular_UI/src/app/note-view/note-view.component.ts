import { Component, OnInit } from '@angular/core';
import { Note } from '../note';
import { NotesService } from '../services/notes.service';

@Component({
  selector: 'app-note-view',
  templateUrl: './note-view.component.html',
  styleUrls: ['./note-view.component.css']
})
export class NoteViewComponent implements OnInit {

  note: Note = new Note();
  notes: Array<Note> = [];
  errMessage: string;
  constructor(private noteService: NotesService) { }

  ngOnInit() {
    this.noteService.getNotes().subscribe(
      data => this.notes = data,
      err => this.errMessage = 'Http failure response for getNotes(): 404 Not Found'
    );
    console.log(this.notes);
  }

}
