import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BranchService } from './branch-service.model';
import { BranchServicePopupService } from './branch-service-popup.service';
import { BranchServiceService } from './branch-service.service';

@Component({
    selector: 'jhi-branch-service-delete-dialog',
    templateUrl: './branch-service-delete-dialog.component.html'
})
export class BranchServiceDeleteDialogComponent {

    branchService: BranchService;

    constructor(
        private branchServiceService: BranchServiceService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.branchServiceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'branchServiceListModification',
                content: 'Deleted an branchService'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-branch-service-delete-popup',
    template: ''
})
export class BranchServiceDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private branchServicePopupService: BranchServicePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.branchServicePopupService
                .open(BranchServiceDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
