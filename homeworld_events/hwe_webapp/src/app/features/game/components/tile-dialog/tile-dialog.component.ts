import { Component, Inject } from '@angular/core';
import { MatDialogModule, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule, FormControl } from '@angular/forms';
import { NgIf } from "@angular/common";
import { TileModel } from '../../models/tile.model';

@Component({
  selector: 'app-tile-dialog',
  standalone: true,
  imports: [MatDialogModule, MatButtonModule, MatCheckboxModule, MatFormFieldModule, 
    MatInputModule, ReactiveFormsModule, NgIf],
  templateUrl: './tile-dialog.component.html',
  styleUrl: './tile-dialog.component.css'
})
export class TileDialog {
  isReservedCtrl: FormControl<boolean>;
  reservedByCtrl: FormControl<string>;
  isCompletedCtrl: FormControl<boolean>;
  completedByCtrl: FormControl<string>;
  isEdit: boolean = false;

  constructor(public tileDialog: MatDialogRef<TileDialog>, @Inject(MAT_DIALOG_DATA) public tile: TileModel) {
    this.isReservedCtrl = new FormControl<boolean>(tile?.isReserved ?? false, { nonNullable: true });
    this.reservedByCtrl = new FormControl<string>(tile?.reservedBy ?? '', { nonNullable: true });
    this.isCompletedCtrl = new FormControl<boolean>(tile?.isCompleted ?? false, { nonNullable: true });
    this.completedByCtrl = new FormControl<string>(tile?.completedBy ?? '', { nonNullable: true });
  }

  onEdit() {
    this.isEdit = true;
  }

  onSave() {
    this.isEdit = false;
    this.tileDialog.close({ 
      isReserved: this.isReservedCtrl.value,
      reservedBy: this.reservedByCtrl.value,
      isCompleted: this.isCompletedCtrl.value,
      completedBy: this.completedByCtrl.value
    });
  }

  onClose() {
    this.isEdit = false;
    this.tileDialog.close();
  }

  showIsReserved() {
    return this.isEdit && !this.isCompletedCtrl.value;
  }

  showReservedBy() {
    return this.isReservedCtrl.value && this.reservedByCtrl.value !== '' && !this.isCompletedCtrl.value;
  }

  showReservedByInput() {
    return this.isEdit && this.isReservedCtrl.value && !this.isCompletedCtrl.value;
  }

  showIsCompleted() {
    return this.isEdit;
  }

  showCompletedBy() {
    return this.isCompletedCtrl.value && this.completedByCtrl.value !== '';
  }

  showCompletedByInput() {
    return this.isEdit && this.isCompletedCtrl.value;
  }
}
