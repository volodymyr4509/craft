import { BaseEntity, User } from './../../shared';

export class Dog implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public age?: number,
        public photoContentType?: string,
        public photo?: any,
        public master?: User,
    ) {
    }
}
