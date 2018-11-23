package com.example.android.mypopularmovieappstage1.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity (tableName = "movies")
public class FavMovieEntity implements Parcelable {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private Integer id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "posterPath")
    private String posterPath;
    @ColumnInfo(name = "releaseDate")
    private String releaseDate;
    @ColumnInfo(name = "overview")
    private String overview;
    @ColumnInfo(name = "rating")
    private String rating;


    public FavMovieEntity(int id, String title, String posterPath, String overview, String rating, String releaseDate) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.rating = rating;
        this.releaseDate = releaseDate;
    }

    protected FavMovieEntity(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        title = in.readString();
        posterPath = in.readString();
        releaseDate = in.readString();
        overview = in.readString();
        rating = in.readString();
    }

    public static final Creator<FavMovieEntity> CREATOR = new Creator<FavMovieEntity>() {
        @Override
        public FavMovieEntity createFromParcel(Parcel in) {
            return new FavMovieEntity(in);
        }

        @Override
        public FavMovieEntity[] newArray(int size) {
            return new FavMovieEntity[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(releaseDate);
        dest.writeString(overview);
        dest.writeString(rating);
    }
}
