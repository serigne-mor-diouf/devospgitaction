import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ClasseRoutingModule } from './classe-routing.module';
import { ClasseComponent } from './components/classe/classe.component';
import { ClasseDashboardComponent } from './components/classe-dashboard/classe-dashboard.component';

@NgModule({
  declarations: [
    ClasseComponent,
    ClasseDashboardComponent
  ],
  imports: [
    CommonModule,
    ClasseRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule
  ]
})
export class ClasseModule { }
