package com.example.proyectofintrimestre_alejandromoles.Controlador;
//informacion sacada de https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/ItemTouchHelper.Callback
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofintrimestre_alejandromoles.Interfaces.ItemTouch;
import com.example.proyectofintrimestre_alejandromoles.R;

public class ItemCallBack extends ItemTouchHelper.Callback {

    //me creo un objeto de la interfaz que he creado anteriormente
    ItemTouch itemTouch;

    //constructor de la clase ItemCallBack
    public ItemCallBack(ItemTouch itemTouch) {
        this.itemTouch = itemTouch;
    }

    //metodos que se contruyen por defecto al crear la clase
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        final int deslizarF = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(0,deslizarF);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return true;
    }

    //este metodo captura cuando un item se ha deslizado, para luego enviarlo a la interfaz
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        itemTouch.onSwiped(viewHolder,viewHolder.getAdapterPosition());
    }

    //metodo que se usa para mostrar el relativelayout que esta debajo del relatyve layout principal
    //al mostrarlo se veran los elementos que tiene ese relatyve layout
    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if(actionState == ItemTouchHelper.ACTION_STATE_DRAG){
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }else{
            final View foregroundView = ((Adaptador.ViewHolderDatos)viewHolder).view_fondo;
            getDefaultUIUtil().onDrawOver(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);
        }
    }

    //este metodo realiza una animacion al deslizar el item seleccionado
    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if(actionState!=ItemTouchHelper.ACTION_STATE_DRAG){
            final View foregroundView = ((Adaptador.ViewHolderDatos)viewHolder).view_principal;
            getDefaultUIUtil().onDraw(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);
        }
    }


    //este metodo va a cambiar el color del elemento arrastrado al color que se indica, en mi caso me he creado
    //una etiqueta nueva con un color especifico
    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
         final View foregroundView = ((Adaptador.ViewHolderDatos)viewHolder).view_principal;
         foregroundView.setBackgroundColor(ContextCompat.getColor(((

                 Adaptador.ViewHolderDatos
                 )viewHolder).view_principal.getContext(), R.color.gris));
         getDefaultUIUtil().clearView(foregroundView);
    }


    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if(viewHolder != null){
            final View foregroundView = ((Adaptador.ViewHolderDatos)viewHolder).view_principal;
            if(actionState == ItemTouchHelper.ACTION_STATE_DRAG){
                foregroundView.setBackgroundColor(Color.LTGRAY);
            }
            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }
}
