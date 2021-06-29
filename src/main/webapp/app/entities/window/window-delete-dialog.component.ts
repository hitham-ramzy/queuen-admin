import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Window } from './window.model';
import { WindowPopupService } from './window-popup.service';
import { WindowService } from './window.service';

@Component({
    selector: 'jhi-window-delete-dialog',
    templateUrl: './window-delete-dialog.component.html'
})
export class WindowDeleteDialogComponent {

    window: Window;

    constructor(
        private windowService: WindowService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.windowService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'windowListModification',
                content: 'Deleted an window'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-window-delete-popup',
    template: ''
})
export class WindowDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private windowPopupService: WindowPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.windowPopupService
                .open(WindowDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
