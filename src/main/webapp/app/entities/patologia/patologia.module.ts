import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Covid19AppSharedModule } from 'app/shared/shared.module';
import { PatologiaComponent } from './patologia.component';
import { PatologiaDetailComponent } from './patologia-detail.component';
import { PatologiaUpdateComponent } from './patologia-update.component';
import { PatologiaDeleteDialogComponent } from './patologia-delete-dialog.component';
import { patologiaRoute } from './patologia.route';

@NgModule({
  imports: [Covid19AppSharedModule, RouterModule.forChild(patologiaRoute)],
  declarations: [PatologiaComponent, PatologiaDetailComponent, PatologiaUpdateComponent, PatologiaDeleteDialogComponent],
  entryComponents: [PatologiaDeleteDialogComponent]
})
export class Covid19AppPatologiaModule {}
