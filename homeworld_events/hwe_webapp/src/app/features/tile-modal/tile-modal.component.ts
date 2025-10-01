import { Component, Inject } from '@angular/core';
import { MatDialogModule, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule, FormControl } from '@angular/forms';
import { NgIf } from "@angular/common";

export type TileData = { 
  id: number;
  title: string;
  desc: string;
  value: number;
  isReserved: boolean,
  reservedBy: string | undefined,
  isComplete: boolean,
  completedBy: string | undefined
};

@Component({
  selector: 'app-tile-modal',
  standalone: true,
  imports: [MatDialogModule, MatButtonModule, MatCheckboxModule, MatFormFieldModule, 
    MatInputModule, ReactiveFormsModule, NgIf],
  templateUrl: './tile-modal.component.html',
  styleUrl: './tile-modal.component.css'
})
export class TileModal {
  isReservedCtrl: FormControl<boolean>;
  reservedByCtrl: FormControl<string>;
  isCompletedCtrl: FormControl<boolean>;
  completedByCtrl: FormControl<string>;
  isEdit: boolean = false;

  constructor(public tileModal: MatDialogRef<TileModal>, @Inject(MAT_DIALOG_DATA) public tileData: TileData) {
    this.isReservedCtrl = new FormControl<boolean>(tileData?.isReserved ?? false, { nonNullable: true });
    this.reservedByCtrl = new FormControl<string>(tileData?.reservedBy ?? '', { nonNullable: true });
    this.isCompletedCtrl = new FormControl<boolean>(tileData?.isComplete ?? false, { nonNullable: true });
    this.completedByCtrl = new FormControl<string>(tileData?.completedBy ?? '', { nonNullable: true });
  }

  onEdit() {
    this.isEdit = true;
  }

  onSave() {
    this.isEdit = false;
    this.tileModal.close({ 
      isReserved: this.isReservedCtrl.value,
      reservedBy: this.reservedByCtrl.value,
      isComplete: this.isCompletedCtrl.value,
      completedBy: this.completedByCtrl.value
    });
  }

  onClose() {
    this.isEdit = false;
    this.tileModal.close();
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
