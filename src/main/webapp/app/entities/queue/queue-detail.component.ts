import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Queue } from './queue.model';
import { QueueService } from './queue.service';

@Component({
    selector: 'jhi-queue-detail',
    templateUrl: './queue-detail.component.html'
})
export class QueueDetailComponent implements OnInit, OnDestroy {

    queue: Queue;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private queueService: QueueService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInQueues();
    }

    load(id) {
        this.queueService.find(id).subscribe((queue) => {
            this.queue = queue;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInQueues() {
        this.eventSubscriber = this.eventManager.subscribe(
            'queueListModification',
            (response) => this.load(this.queue.id)
        );
    }
}
