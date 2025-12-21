package com.cozi.soft.listing.api;

import io.vavr.control.Try;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.NoSuchElementException;
import java.util.Objects;

@Mapper
public interface CommonMapper {
    CommonMapper INSTANCE = Mappers.getMapper(CommonMapper.class);;

    default OffsetDateTime map(ZonedDateTime value) {
        return Try.success(value)
                .filter(Objects::nonNull)
                .map(zdt -> OffsetDateTime.ofInstant(zdt.toInstant(), ZoneOffset.UTC))
                .recover(NoSuchElementException.class, exception -> null)
                .getOrElseThrow(exception -> new IllegalArgumentException("Failed to convert zoned date time to offset date time", exception));

    }

    default ZonedDateTime map(OffsetDateTime value) {
        return Try.success(value)
                .filter(Objects::nonNull)
                .map(odt -> odt.atZoneSameInstant(ZoneOffset.UTC))
                .recover(NoSuchElementException.class, exception -> null)
                .getOrElseThrow(exception -> new IllegalArgumentException("Failed to convert offset date time to zoned date time", exception));
    }

    default URI map(String value) {
        return Try.success(value)
                .filter(Objects::nonNull)
                .map(URI::create)
                .andThenTry(URI::toURL)
                .recover(NoSuchElementException.class, exception -> null)
                .getOrElseThrow(exception -> new IllegalArgumentException("Failed to convert string to URL", exception));
    }

    default String map(URI value) {
        return Try.success(value)
                .filter(Objects::nonNull)
                .mapTry(URI::toString)
                .recover(NoSuchElementException.class, exception -> null)
                .getOrElseThrow(exception -> new IllegalArgumentException("Failed to convert URL to string", exception));
    }

}
