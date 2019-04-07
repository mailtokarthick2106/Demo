export class Note {
  id: number;
  title: string;
  text: string;
  category: string;
  state: string;

  constructor() {
    this.title = '';
    this.text = '';
    this.category= '';
    this.state = 'not-started';
  }
}
