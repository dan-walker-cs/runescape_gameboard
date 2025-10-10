import { Component, inject, Inject } from '@angular/core';
import { MatDialogModule, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule, FormControl } from '@angular/forms';
import { NgFor, NgIf } from "@angular/common";
import { TileModel } from '../../models/tile.model';
import { PlayerStore } from '../../data/player-store.service';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { isNullorBlankString } from '../../../../shared/util/common-utils';

@Component({
  selector: 'app-tile-dialog',
  standalone: true,
  imports: [MatDialogModule, MatButtonModule, MatCheckboxModule, MatFormFieldModule, 
    MatInputModule, MatOptionModule, MatSelectModule, ReactiveFormsModule, NgIf, NgFor],
  templateUrl: './tile-dialog.component.html',
  styleUrl: './tile-dialog.component.css'
})
export class TileDialogComponent {
    // Load-once immutable Player data from backend
    readonly playerStore = inject(PlayerStore);

    // "Edit Mode" flag
    isEdit: boolean = false;

    /* TODO: Potentially refactor using FormGroup? */
    // Form controls for tile-dialog
    isReservedCtrl: FormControl<boolean>;
    reservedByCtrl: FormControl<string>;
    isCompletedCtrl: FormControl<boolean>;
    completedByCtrl: FormControl<string>;

    constructor(public tileDialog: MatDialogRef<TileDialogComponent>, @Inject(MAT_DIALOG_DATA) public tile: TileModel) {
        this.isReservedCtrl = new FormControl<boolean>(tile?.isReserved ?? false, { nonNullable: true });
        this.reservedByCtrl = new FormControl<string>(tile?.reservedBy ?? '', { nonNullable: true });
        this.isCompletedCtrl = new FormControl<boolean>(tile?.isCompleted ?? false, { nonNullable: true });
        this.completedByCtrl = new FormControl<string>(tile?.completedBy ?? '', { nonNullable: true });
    }

    /** Enter "Edit Mode" */
    onEdit(): void {
        this.isEdit = true;
    }

  /** Conditionally disable the Save button when dialog checkbox selected without Player input. */
    disableSave(): boolean {
        return (this.isReservedCtrl.value && isNullorBlankString(this.reservedByCtrl.value)) ||
            (this.isCompletedCtrl.value && isNullorBlankString(this.completedByCtrl.value));
    }

  /** Executed on Save button click from dialog window. */
    onSave(): void {
        this.isEdit = false;

        this.tileDialog.close({ 
            isReserved: this.isReservedCtrl.value,
            reservedBy: this.reservedByCtrl.value,
            isCompleted: this.isCompletedCtrl.value,
            completedBy: this.completedByCtrl.value
        });
    }

    /** Executed on Close button click from dialog window. */
    onClose(): void {
        this.isEdit = false;
        this.tileDialog.close();
    }

    /** Conditionally show the isReserved checkbox - when not editing "Completed" values. */
    showIsReserved(): boolean {
        return this.isEdit && !this.isCompletedCtrl.value;
    }

    /** Conditionally show the reservedBy dropdown after checking the isReserved checkbox. */
    showReservedByInput(): boolean {
        return this.isEdit && this.isReservedCtrl.value && !this.isCompletedCtrl.value;
    }

    /** Conditionally show the "Reserved" values in dialog when a Tile is reserved & validated. */
    showReservedBy(): boolean {
        return this.isReservedCtrl.value && this.reservedByCtrl.value !== '' && !this.isCompletedCtrl.value;
    }

    /** Conditionally show the isCompleted checkbox - when not editing "Reserved" values. */
    showIsCompleted(): boolean {
        return this.isEdit;
    }

    /** Conditionally show the completedBy dropdown after checking the isCompleted checkbox. */
    showCompletedByInput(): boolean {
        return this.isEdit && this.isCompletedCtrl.value;
    }

    /** Conditionally show the "Completed" values in dialog when a Tile is completed & validated. */
    showCompletedBy(): boolean {
        return this.isCompletedCtrl.value && this.completedByCtrl.value !== '';
    }
}
