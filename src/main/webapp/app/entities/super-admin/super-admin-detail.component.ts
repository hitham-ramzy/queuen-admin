import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { SuperAdmin } from './super-admin.model';
import { SuperAdminService } from './super-admin.service';

@Component({
    selector: 'jhi-super-admin-detail',
    templateUrl: './super-admin-detail.component.html'
})
export class SuperAdminDetailComponent implements OnInit, OnDestroy {

    superAdmin: SuperAdmin;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private superAdminService: SuperAdminService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSuperAdmins();
    }

    load(id) {
        this.superAdminService.find(id).subscribe((superAdmin) => {
            this.superAdmin = superAdmin;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSuperAdmins() {
        this.eventSubscriber = this.eventManager.subscribe(
            'superAdminListModification',
            (response) => this.load(this.superAdmin.id)
        );
    }
}
