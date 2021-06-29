import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Queue } from './queue.model';
import { QueuePopupService } from './queue-popup.service';
import { QueueService } from './queue.service';

@Component({
    selector: 'jhi-queue-delete-dialog',
    templateUrl: './queue-delete-dialog.component.html'
})
export class QueueDeleteDialogComponent {

    queue: Queue;

    constructor(
        private queueService: QueueService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.queueService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'queueListModification',
                content: 'Deleted an queue'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-queue-delete-popup',
    template: ''
})
export class QueueDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private queuePopupService: QueuePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.queuePopupService
                .open(QueueDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
