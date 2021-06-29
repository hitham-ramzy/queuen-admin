import { BaseEntity } from './../../shared';

export const enum QueueStatus {
    'ACTIVE',
    'INACTIVE',
    'ENDED'
}

export class Queue implements BaseEntity {
    constructor(
        public id?: number,
        public status?: QueueStatus,
        public day?: any,
        public currentServingNumber?: number,
        public lastReservedNumber?: number,
        public lastStatusBySuperAdmin?: boolean,
        public service?: BaseEntity,
    ) {
        this.lastStatusBySuperAdmin = false;
    }
}
