import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Covid19AppSharedModule } from 'app/shared/shared.module';
import { SintomoComponent } from './sintomo.component';
import { SintomoDetailComponent } from './sintomo-detail.component';
import { SintomoUpdateComponent } from './sintomo-update.component';
import { SintomoDeleteDialogComponent } from './sintomo-delete-dialog.component';
import { sintomoRoute } from './sintomo.route';

@NgModule({
  imports: [Covid19AppSharedModule, RouterModule.forChild(sintomoRoute)],
  declarations: [SintomoComponent, SintomoDetailComponent, SintomoUpdateComponent, SintomoDeleteDialogComponent],
  entryComponents: [SintomoDeleteDialogComponent]
})
export class Covid19AppSintomoModule {}
