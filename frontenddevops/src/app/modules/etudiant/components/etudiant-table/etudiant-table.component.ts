import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-etudiant-table',
  templateUrl: './etudiant-table.component.html',
  styleUrls: ['./etudiant-table.component.scss']
})
export class EtudiantTableComponent {
  @Input() etudiants: any[] = [];
  @Output() edit = new EventEmitter<any>();
  @Output() delete = new EventEmitter<any>();
  @Output() view = new EventEmitter<any>();

  onEdit(etudiant: any) {
    this.edit.emit(etudiant);
  }

  onDelete(etudiant: any) {
    this.delete.emit(etudiant);
  }

  onView(etudiant: any) {
    this.view.emit(etudiant);
  }
} 