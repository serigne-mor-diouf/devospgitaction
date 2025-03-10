import { NgModule } from '@angular/core';
import { CoursComponent } from './cours/cours.component';
import { DashboardCoursComponent } from './dashboard-cours/dashboard-cours.component';
import { CoursDetailComponent } from './cours-detail/cours-detail.component';
import { CoursRoutingModule } from './cours-routing.module';
import { SharedCoursModule } from './shared/shared-cours.module';

@NgModule({
  declarations: [
    CoursComponent,
    DashboardCoursComponent,
    CoursDetailComponent
  ],
  imports: [
    SharedCoursModule,
    CoursRoutingModule
  ],
  exports: [
    CoursComponent,
    DashboardCoursComponent,
    CoursDetailComponent
  ]
})
export class CoursModule { }
