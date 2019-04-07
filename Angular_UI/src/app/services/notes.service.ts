import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Observable } from 'rxjs/Observable';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Note } from '../note';
import { AuthenticationService } from '../services/authentication.service';
import { tap } from 'rxjs/operators';

const API_URL = 'http://localhost:8765/api/NoteService/api/v1/note/';

@Injectable()
export class NotesService {
  notes: Array<Note>;
  note: Note = new Note();
  noteSubject: BehaviorSubject<Array<Note>>;
  token: any;
  constructor(private http: HttpClient, private _authService: AuthenticationService) {
    this.notes = [];
    this.noteSubject = new BehaviorSubject(this.notes);
  }

  fetchNotesFromServer() {
    this.token = this._authService.getBearerToken();
    const uri = API_URL+this._authService.getUserId();
    return this.http.get<Array<Note>>(uri, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${this.token}`)
    }).subscribe(notes => {
      this.notes = notes;
      this.noteSubject.next(this.notes);
    });
  }

  getNotes(): BehaviorSubject<Array<Note>> {
    return this.noteSubject;
  }

  addNote(note: Note): Observable<Note> {
    const uri = API_URL+this._authService.getUserId();
    return this.http.post<Note>(uri, note, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${this._authService.getBearerToken()}`)
    }).pipe(tap(addnote => {
      this.notes.push(addnote);
      this.noteSubject.next(this.notes);
    }
    ));
  }

  editNote(note: Note): Observable<Note> {
    const uri = API_URL+this._authService.getUserId()+`/+${note.id}`;
    return this.http.put<Note>(uri, note, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${this._authService.getBearerToken()}`)
    }).pipe(tap(editnote => {
      const notet = this.notes.find(notex => notex.id === editnote.id);
      Object.assign(notet, editnote);
      this.noteSubject.next(this.notes);
    }
    ));
  }

  deleteNoteById(noteId: number): void {
    const uri = API_URL+this._authService.getUserId()+`/+${noteId}`;
    console.log(uri);
    this.http.delete(uri, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${this._authService.getBearerToken()}`)
    }).subscribe(
      done => {
        const index = this.notes.map(note => {
          return note.id;
        }).indexOf(noteId);
        this.notes.splice(index, 1);
        this.noteSubject.next(this.notes);
      },
      err => { }
    );
  }

  getNoteById(noteId): Note {
    const note = this.notes.find(notex => notex.id === noteId);
     return Object.assign({}, note);
  }
}
