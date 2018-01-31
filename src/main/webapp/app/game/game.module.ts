import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {CraftSharedModule} from '../shared';

import {GAME_ROUTE, GameComponent} from './';

@NgModule({
    imports: [
        CraftSharedModule,
        RouterModule.forChild([GAME_ROUTE])
    ],
    declarations: [
        GameComponent,
    ],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftGameModule {
}
