package com.stackroute.keepnote.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.exception.NoteNotFoundExeption;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.service.NoteService;

/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
//@FeignClient(name = "NoteController")
//@RibbonClient(name = "NoteController")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class NoteController {
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	/*
	 * Autowiring should be implemented for the NoteService. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword
	 */
	@Autowired
	private NoteService noteService;
	public NoteController(NoteService noteService) {
		this.noteService=noteService;
	}

	/*
	 * Define a handler method which will create a specific note by reading the
	 * Serialized object from request body and save the note details in the
	 * database.This handler method should return any one of the status messages
	 * basis on different situations: 
	 * 1. 201(CREATED) - If the note created successfully. 
	 * 2. 409(CONFLICT) - If the noteId conflicts with any existing user.
	 * 
	 * This handler method should map to the URL "/api/v1/note" using HTTP POST method
	 */
	@PostMapping(value = "/api/v1/note/{userId}")
	public ResponseEntity<Note> registerNote(@PathVariable String userId, @RequestBody Note note) {
		boolean insertStatus=false;
		try {
			insertStatus=this.noteService.createNote(userId, note);
			if(insertStatus){
			return new ResponseEntity<Note>(note, HttpStatus.CREATED);
			}
			else{
				return new ResponseEntity<Note>(HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
			// TODO Auto-generated catch block
			return new ResponseEntity<Note>(HttpStatus.CONFLICT);
		
		}
	}
	/*
	 * Define a handler method which will delete a note from a database.
	 * This handler method should return any one of the status messages basis 
	 * on different situations: 
	 * 1. 200(OK) - If the note deleted successfully from database. 
	 * 2. 404(NOT FOUND) - If the note with specified noteId is not found.
	 *
	 * This handler method should map to the URL "/api/v1/note/{id}" using HTTP Delete
	 * method" where "id" should be replaced by a valid noteId without {}
	 */
	@DeleteMapping("/api/v1/note/{userId}/{id}")
	public ResponseEntity<Boolean> deleteNote(@PathVariable String userId,
			@PathVariable int id) {
		System.out.println("Fetching & Deleting note with id " + id);
		boolean status = false;
		Note note;
		try {
			note = this.noteService.getNoteByNoteId(userId, id);
			if (note == null) {
				System.out.println("Unable to delete. note with id " + id + " not found");
				return new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
			}

			status = this.noteService.deleteNote(userId, id);
			return new ResponseEntity<Boolean>(status, HttpStatus.OK);
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			return new ResponseEntity<Boolean>(false,HttpStatus.NOT_FOUND);
		}
	
	}

	/*
	 * Define a handler method which will update a specific note by reading the
	 * Serialized object from request body and save the updated note details in a
	 * database. 
	 * This handler method should return any one of the status messages
	 * basis on different situations: 
	 * 1. 200(OK) - If the note updated successfully.
	 * 2. 404(NOT FOUND) - If the note with specified noteId is not found.
	 * 
	 * This handler method should map to the URL "/api/v1/note/{id}" using HTTP PUT method.
	 */
	@PutMapping("/api/v1/note/{userId}/{id}")
	public ResponseEntity<Note> updateNote(@PathVariable String userId,
			@PathVariable int id,
			@RequestBody Note note) {
		Note currentNote = null;
		try {
			currentNote = this.noteService.getNoteByNoteId(userId, id);
			if (currentNote == null) {
				System.out.println("category with id " + id + " not found");
				return new ResponseEntity<Note>(HttpStatus.NOT_FOUND);
			}

			currentNote = this.noteService.updateNote(note, id, userId);
			return new ResponseEntity<Note>(currentNote, HttpStatus.OK);
		} catch (NoteNotFoundExeption e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Note>(HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * Define a handler method which will get us the all notes by a userId.
	 * This handler method should return any one of the status messages basis on
	 * different situations: 
	 * 1. 200(OK) - If the note found successfully. 
	 * 
	 * This handler method should map to the URL "/api/v1/note" using HTTP GET method
	 */
	
	/*
	 * Define a handler method which will show details of a specific note created by specific 
	 * user. This handler method should return any one of the status messages basis on
	 * different situations: 
	 * 1. 200(OK) - If the note found successfully. 
	 * 2. 404(NOT FOUND) - If the note with specified noteId is not found.
	 * This handler method should map to the URL "/api/v1/note/{userId}/{noteId}" using HTTP GET method
	 * where "id" should be replaced by a valid reminderId without {}
	 * 
	 */
	/*@GetMapping("/api/v1/note/{userId}/{noteId}")
	public ResponseEntity<Note> getNote(@PathVariable("userId") String userId,int noteId) {
		Note note = null;
		try {

			note = this.noteService.getNoteByNoteId(userId, noteId);
			if (note == null) {
				return new ResponseEntity<Note>(HttpStatus.NOT_FOUND);
			}

		} catch (NoteNotFoundExeption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<Note>(note, HttpStatus.OK);
	}*/
	@GetMapping("/api/v1/note/{userId}")
	public ResponseEntity<Note[]> getNotesByUserId(@PathVariable("userId") String userId) {
		List<Note> notes = null;
		Note[] noteArray=null;
		try {
            System.out.println(userId+"<<<<<user values>>>>>>>>>>>>>>>>>>");
			notes = this.noteService.getAllNoteByUserId(userId);
			
			if (notes == null) {
				return new ResponseEntity<Note[]>(HttpStatus.NOT_FOUND);
			}
			noteArray=notes.stream().toArray(n-> new Note[n]);
		} catch (NoteNotFoundExeption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<Note[]>(noteArray, HttpStatus.OK);
	}
}
