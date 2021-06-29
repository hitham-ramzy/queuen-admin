import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SuperAdmin } from './super-admin.model';
import { SuperAdminPopupService } from './super-admin-popup.service';
import { SuperAdminService } from './super-admin.service';

@Component({
    selector: 'jhi-super-admin-delete-dialog',
    templateUrl: './super-admin-delete-dialog.component.html'
})
export class SuperAdminDeleteDialogComponent {

    superAdmin: SuperAdmin;

    constructor(
        private superAdminService: SuperAdminService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.superAdminService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'superAdminListModification',
                content: 'Deleted an superAdmin'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-super-admin-delete-popup',
    template: ''
})
export class SuperAdminDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private superAdminPopupService: SuperAdminPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.superAdminPopupService
                .open(SuperAdminDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
