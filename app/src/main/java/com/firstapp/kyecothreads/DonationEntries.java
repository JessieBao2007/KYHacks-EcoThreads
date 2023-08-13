package com.firstapp.kyecothreads;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class DonationEntries implements Parcelable {
    private String title;
    private int quantity;
    private String clothingType;
    private Uri imageUri;

    public DonationEntries(String title, int quantity, String clothingType, Uri imageUri) {
        this.title = title;
        this.quantity = quantity;
        this.clothingType = clothingType;
        this.imageUri = imageUri;
    }

    public String getTitle() {
        return title;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getClothingType() {
        return clothingType;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    // Parcelable implementation

    protected DonationEntries(Parcel in) {
        title = in.readString();
        quantity = in.readInt();
        clothingType = in.readString();
        imageUri = in.readParcelable(Uri.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(quantity);
        dest.writeString(clothingType);
        dest.writeParcelable(imageUri, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DonationEntries> CREATOR = new Creator<DonationEntries>() {
        @Override
        public DonationEntries createFromParcel(Parcel in) {
            return new DonationEntries(in);
        }

        @Override
        public DonationEntries[] newArray(int size) {
            return new DonationEntries[size];
        }
    };
}
