import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISintomo } from 'app/shared/model/sintomo.model';
import { SintomoService } from './sintomo.service';

@Component({
  templateUrl: './sintomo-delete-dialog.component.html'
})
export class SintomoDeleteDialogComponent {
  sintomo?: ISintomo;

  constructor(protected sintomoService: SintomoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sintomoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('sintomoListModification');
      this.activeModal.close();
    });
  }
}
