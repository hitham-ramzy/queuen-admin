import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QueueNSharedModule } from '../../shared';
import {
    QueueService,
    QueuePopupService,
    QueueComponent,
    QueueDetailComponent,
    QueueDialogComponent,
    QueuePopupComponent,
    QueueDeletePopupComponent,
    QueueDeleteDialogComponent,
    queueRoute,
    queuePopupRoute,
    QueueResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...queueRoute,
    ...queuePopupRoute,
];

@NgModule({
    imports: [
        QueueNSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        QueueComponent,
        QueueDetailComponent,
        QueueDialogComponent,
        QueueDeleteDialogComponent,
        QueuePopupComponent,
        QueueDeletePopupComponent,
    ],
    entryComponents: [
        QueueComponent,
        QueueDialogComponent,
        QueuePopupComponent,
        QueueDeleteDialogComponent,
        QueueDeletePopupComponent,
    ],
    providers: [
        QueueService,
        QueuePopupService,
        QueueResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QueueNQueueModule {}
