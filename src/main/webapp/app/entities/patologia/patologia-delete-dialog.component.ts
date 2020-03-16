import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPatologia } from 'app/shared/model/patologia.model';
import { PatologiaService } from './patologia.service';

@Component({
  templateUrl: './patologia-delete-dialog.component.html'
})
export class PatologiaDeleteDialogComponent {
  patologia?: IPatologia;

  constructor(protected patologiaService: PatologiaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.patologiaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('patologiaListModification');
      this.activeModal.close();
    });
  }
}
