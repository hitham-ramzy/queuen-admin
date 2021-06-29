import { BaseEntity } from './../../shared';

export const enum SessionStatus {
    'STARTED',
    'ENDED',
    'MISSED'
}

export class Session implements BaseEntity {
    constructor(
        public id?: number,
        public rateValue?: number,
        public reviewDescription?: any,
        public startDateTime?: any,
        public endDateTime?: any,
        public servingMinutes?: number,
        public windowName?: string,
        public status?: SessionStatus,
        public agent?: BaseEntity,
        public customer?: BaseEntity,
    ) {
    }
}
