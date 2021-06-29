import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QueueNSharedModule } from '../../shared';
import {
    WindowService,
    WindowPopupService,
    WindowComponent,
    WindowDetailComponent,
    WindowDialogComponent,
    WindowPopupComponent,
    WindowDeletePopupComponent,
    WindowDeleteDialogComponent,
    windowRoute,
    windowPopupRoute,
    WindowResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...windowRoute,
    ...windowPopupRoute,
];

@NgModule({
    imports: [
        QueueNSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        WindowComponent,
        WindowDetailComponent,
        WindowDialogComponent,
        WindowDeleteDialogComponent,
        WindowPopupComponent,
        WindowDeletePopupComponent,
    ],
    entryComponents: [
        WindowComponent,
        WindowDialogComponent,
        WindowPopupComponent,
        WindowDeleteDialogComponent,
        WindowDeletePopupComponent,
    ],
    providers: [
        WindowService,
        WindowPopupService,
        WindowResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QueueNWindowModule {}
