import { BaseEntity } from './../../shared';

export const enum Day {
    'SATURDAY',
    'SUNDAY',
    'MONDAY',
    'TUESDAY',
    'WEDNESDAY',
    'THURSDAY',
    'FRIDAY'
}

export class Branch implements BaseEntity {
    constructor(
        public id?: number,
        public startWorkingHours?: any,
        public endWorkingHours?: any,
        public workingDays?: Day,
        public acceptedParallelReservationNumber?: number,
        public createdAt?: any,
        public createdBy?: string,
        public active?: boolean,
        public lastDeactivationBySuperAdmin?: boolean,
        public services?: BaseEntity[],
        public company?: BaseEntity,
    ) {
        this.active = false;
        this.lastDeactivationBySuperAdmin = false;
    }
}
